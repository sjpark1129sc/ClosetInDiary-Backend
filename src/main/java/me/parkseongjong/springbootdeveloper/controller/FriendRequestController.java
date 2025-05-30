package me.parkseongjong.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.parkseongjong.springbootdeveloper.domain.FriendRequest;
import me.parkseongjong.springbootdeveloper.domain.User;
import me.parkseongjong.springbootdeveloper.service.FriendRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friend-requests")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    // Method to send friend request by ID
    @PostMapping("/send/by-id")
    public ResponseEntity<String> sendFriendRequestById(@AuthenticationPrincipal User user, @RequestParam Long id) {
        friendRequestService.sendFriendRequestById(user.getId(), id);
        return ResponseEntity.ok("Friend request sent successfully by ID.");
    }

    // Method to send friend request by username
    @PostMapping("/send/by-username")
    public ResponseEntity<String> sendFriendRequestByUsername(@AuthenticationPrincipal User user, @RequestParam String username) {
        friendRequestService.sendFriendRequestByUsername(user.getId(), username);
        return ResponseEntity.ok("Friend request sent successfully by username.");
    }

    // Method to send friend request by email
    @PostMapping("/send/by-email")
    public ResponseEntity<String> sendFriendRequestByEmail(@AuthenticationPrincipal User user, @RequestParam String email) {
        friendRequestService.sendFriendRequestByEmail(user.getId(), email);
        return ResponseEntity.ok("Friend request sent successfully by email.");
    }

    // 친구 요청 수락
    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@AuthenticationPrincipal User user, @RequestParam Long requestId) {
        boolean isAccepted = friendRequestService.acceptFriendRequest(user.getId(), requestId);
        if (isAccepted) {
            return ResponseEntity.ok("Friend request accepted.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to accept this request.");
        }
    }

    // 친구 요청 거절
    @PostMapping("/decline")
    public ResponseEntity<String> declineFriendRequest(@AuthenticationPrincipal User user, @RequestParam Long requestId) {
        boolean isDeclined = friendRequestService.declineFriendRequest(user.getId(), requestId);
        if (isDeclined) {
            return ResponseEntity.ok("Friend request declined.");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized to decline this request.");
        }
    }

    // 친구 요청 목록 조회 (받은 요청)
    @GetMapping("/received")
    public ResponseEntity<List<FriendRequest>> getReceivedRequests(@AuthenticationPrincipal User user) {
        List<FriendRequest> requests = friendRequestService.getReceivedFriendRequests(user.getId());
        return ResponseEntity.ok(requests);
    }

    // 친구 요청 목록 조회 (보낸 요청)
    @GetMapping("/sent")
    public ResponseEntity<List<FriendRequest>> getSentRequests(@AuthenticationPrincipal User user) {
        List<FriendRequest> requests = friendRequestService.getSentFriendRequests(user.getId());
        return ResponseEntity.ok(requests);
    }
}

