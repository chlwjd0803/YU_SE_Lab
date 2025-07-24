import com.example.geminitodo.dto.CategoryDto;
import com.example.geminitodo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos/index")
public class CategoryApiController {
    private final CategoryService categoryService;

    private Long getUserId() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@RequestBody CategoryDto dto) {
        return ResponseEntity.ok(categoryService.addCategory(getUserId(), dto));
    }

    @PatchMapping("/category/{id}")
    public ResponseEntity<?> editCategory(@PathVariable Long id, @RequestBody CategoryDto dto) {
        return ResponseEntity.ok(categoryService.editCategory(id, dto));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/category")
    public ResponseEntity<?> deleteAllCategories() {
        categoryService.deleteAllCategories(getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/category")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories(getUserId()));
    }
}
