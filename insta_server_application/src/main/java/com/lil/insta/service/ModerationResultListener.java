// package com.lil.insta.service;

// import java.io.StringReader;
// import java.util.Optional;

// import javax.json.Json;
// import javax.json.JsonObject;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Profile;
// import org.springframework.kafka.annotation.KafkaHandler;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;

// import com.lil.insta.entity.CommentInfo;
// import com.lil.insta.entity.ModerationStatus;
// import com.lil.insta.repository.CommentRepository;

// @Service
// @Profile("kafka")
// @KafkaListener(topics = "moderation-result", groupId = "java-listener")
// public class ModerationResultListener {
//     private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ModerationResultListener.class);
//     private CommentRepository commentRepository;

//     @Autowired
//     public ModerationResultListener(CommentRepository commentRepository) {
//         this.commentRepository = commentRepository;
//     }

//     @KafkaHandler
//     public void consume(String message) {
//         try {
//             JsonObject json = Json.createReader(new StringReader(message)).readObject();
//             String commentId = json.getString("commentId");
//             double toxicityScore = json.getJsonNumber("toxicity_score").doubleValue();
//             String moderationStatus = json.getString("moderationStatus");
//             Optional<CommentInfo> result = commentRepository.findById(commentId);
//             if (result.isEmpty())
//                 return;
//             CommentInfo commentInfo = result.get();
//             commentInfo.setToxicityScore(toxicityScore);
//             commentInfo.setModerationStatus(ModerationStatus.valueOf(moderationStatus));
//             commentRepository.save(commentInfo);
//         } catch (Exception e) {
//             log.error("Failed to process moderation result", e);
//         }
//     }
// }
