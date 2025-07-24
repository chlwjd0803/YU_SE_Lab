import com.example.geminitodo.dto.TodoDto;
import com.example.geminitodo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoApiController {
    private final TodoService todoService;

    private Long getUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("/index")
    public ResponseEntity<?> getTasks(@RequestParam(required = false, defaultValue = "준비") String status) {
        return ResponseEntity.ok(todoService.getTasks(getUserId(), status));
    }

    @PostMapping("/index/task")
    public ResponseEntity<?> addTask(@RequestBody TodoDto dto) {
        return ResponseEntity.ok(todoService.addTask(getUserId(), dto));
    }

    @PostMapping("/today/task")
    public ResponseEntity<?> addTodayTask(@RequestBody TodoDto dto) {
        dto.setDeadline(LocalDate.now());
        return ResponseEntity.ok(todoService.addTask(getUserId(), dto));
    }

    @PostMapping("/favorite/task")
    public ResponseEntity<?> addFavoriteTask(@RequestBody TodoDto dto) {
        dto.setFavorite(true);
        return ResponseEntity.ok(todoService.addTask(getUserId(), dto));
    }

    @PostMapping("/index/updateStatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody String status) {
        return ResponseEntity.ok(todoService.updateStatus(id, status));
    }

    @PatchMapping("/updateFavorite/{id}")
    public ResponseEntity<?> updateFavorite(@PathVariable Long id) {
        todoService.updateFavorite(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/index/task/{id}")
    public ResponseEntity<?> editTask(@PathVariable Long id, @RequestBody TodoDto dto) {
        return ResponseEntity.ok(todoService.editTask(id, dto));
    }

    @DeleteMapping("/index/task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        todoService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
