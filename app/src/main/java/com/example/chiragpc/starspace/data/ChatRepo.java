package com.example.chiragpc.starspace.data;

import com.example.chiragpc.starspace.data.callbacks.OnTaskCompletion;
import com.example.chiragpc.starspace.model.ChatMessage;
import com.example.chiragpc.starspace.model.MessageTime;
import com.example.chiragpc.starspace.model.UserAccount;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;

/**
 * Created by Chirag on 1/8/2019 at 19:54.
 * Project - StarSpace
 */
class ChatRepo {

    private FirebaseRepo mFirebaseRepo;

    ChatRepo(FirebaseRepo firebaseRepo) {
        this.mFirebaseRepo = firebaseRepo;
    }

    void saveMessagesChatRepo(String message, String senderId, String receiverId) {
        //  Add Messages in Sender User Firestore Account
        mFirebaseRepo.getChatDatabaseReferenceInstace()
                .document(senderId + receiverId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            String chatId = task.getResult().getId();

                            List<MessageTime> messageTimeList = new ArrayList<>();
                            if (task.getResult() != null && task.getResult().toObject(ChatMessage.class) != null) {
                                messageTimeList = task.getResult().toObject(ChatMessage.class).getMessages();
                            }

                            //  Add "messages" label in database
                            if (messageTimeList == null) {
                                HashMap<String, String> messageLabel = new HashMap<>();
                                messageLabel.put("messages", null);
                                mFirebaseRepo
                                        .getChatDatabaseReferenceInstace()
                                        .document(chatId)
                                        .set(messageLabel, SetOptions.merge());
                            }
                            //  Merge messages to database
                            List<MessageTime> textMessage = new ArrayList<>();
                            HashMap<String, List<MessageTime>> messageText = new HashMap<>();
                            MessageTime text = new MessageTime(message, System.currentTimeMillis(), senderId, receiverId);

                            if (messageTimeList != null) {
                                textMessage = messageTimeList;
                            }
                            textMessage.add(text);
                            messageText.put("messages", textMessage);

                            mFirebaseRepo
                                    .getChatDatabaseReferenceInstace()
                                    .document(chatId)
                                    .set(messageText, SetOptions.mergeFields("messages"));

                            //  Add Message Id to Sender User Database
                            addMessageIdToUserDatabase(chatId, senderId);

                            //  Add Message Id to Receiver User Database
                            addMessageIdToUserDatabase(chatId, receiverId);
                        }
                    }
                });
    }

    private void addMessageIdToUserDatabase(String id, String userId) {
        mFirebaseRepo.getUserDatabaseReferenceInstance()
                .document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.toObject(UserAccount.class) != null) {
                            UserAccount account = documentSnapshot.toObject(UserAccount.class);

                            List<String> messageFriends = null;
                            if (account != null && account.getMessageId() != null) {
                                messageFriends = account.getMessageId();
                            }
                            if (messageFriends != null) {
                                if (!messageFriends.contains(id)) {
                                    messageFriends.add(id);

                                }
                            } else {
                                messageFriends = new ArrayList<>();
                                messageFriends.add(id);
                            }

                            HashMap<String, List<String>> messages = new HashMap<>();
                            messages.put("messageId", messageFriends);

                            mFirebaseRepo
                                    .getUserDatabaseReferenceInstance()
                                    .document(userId)
                                    .set(messages, SetOptions.mergeFields("messageId"));
                        }
                    }
                });
    }

    void getMessagesChatRepo(String senderId, String receiverId, OnTaskCompletion.GetMessages taskCompletion) {
        Logger.addLogAdapter(new AndroidLogAdapter());
        mFirebaseRepo
                .getUserDatabaseReferenceInstance()
                .document(senderId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot != null && documentSnapshot.toObject(UserAccount.class) != null) {
                            List<String> message = documentSnapshot.toObject(UserAccount.class).getMessageId();
                            if (message != null) {
                                mFirebaseRepo
                                        .getUserDatabaseReferenceInstance()
                                        .document(receiverId)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot != null && documentSnapshot.toObject(UserAccount.class) != null) {
                                                    List<String> messageList = documentSnapshot.toObject(UserAccount.class).getMessageId();
                                                    if (messageList != null) {
                                                        for (String id : message) {
                                                            for (String mId : messageList)
                                                                if (id.contains(mId)) {
                                                                    mFirebaseRepo
                                                                            .getChatDatabaseReferenceInstace()
                                                                            .document(id)
                                                                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                                                @Override
                                                                                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                                                                                    if (documentSnapshot != null && documentSnapshot.exists()) {
                                                                                        if (documentSnapshot.toObject(ChatMessage.class) != null) {
                                                                                            List<MessageTime> listMessages = documentSnapshot.toObject(ChatMessage.class).getMessages();
                                                                                            MessageTime lastText = listMessages.get(listMessages.size() - 1);
                                                                                            Map<String, MessageTime> userMessage = new HashMap<>();
                                                                                            userMessage.put(receiverId, lastText);
                                                                                            taskCompletion.onGetLastMessageSuccess(userMessage);
                                                                                            taskCompletion.onGetMessagesSuccess(listMessages);
                                                                                        }
                                                                                    }
                                                                                    if (e != null) {
                                                                                        taskCompletion.onGetMessagesFailure(e.getMessage());
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                        }
                                                    }
                                                }
                                            }

                                        });
                            }
                        }
                    }
                });
    }

    void getLastMessageChatRepo(String senderId, List<String> friendsId, OnTaskCompletion.GetLastMessages taskCompletion) {
//        Logger.addLogAdapter(new AndroidLogAdapter());
//        Map<String, MessageTime> userMessage = new HashMap<>();
//        for (String userId : friendsId) {
//            mFirebaseRepo.getChatDatabaseReferenceInstace()
//                    .document(senderId + userId)
//                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                            if (documentSnapshot != null && documentSnapshot.exists()) {
//                                if (documentSnapshot.toObject(ChatMessage.class) != null) {
//                                    List<MessageTime> listMessages = documentSnapshot.toObject(ChatMessage.class).getMessages();
//                                    MessageTime lastText = listMessages.get(listMessages.size() - 1);
//
//                                    userMessage.put(userId, lastText);
//                                    taskCompletion.onGetLastMessageSuccess(userMessage);
//
//                                }
//                            }
//
//                        }
//                    });
//        }
    }
}
