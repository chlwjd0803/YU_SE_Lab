import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class ReturnSlashChecker {

    public static void main(String[] args) throws IOException {
        // 실험 시 파일 위치를 맞춰서 실행해볼 것.
//        String filePath = "/Users/chlwjd/TodoControllerBefore.java"; // 무한 리다이렉트 오류가 있는 파일
        String filePath = "/Users/chlwjd/TodoControllerAfter.java"; // 무한 리다이렉트 오류가 해결된 파일

        String code = Files.readString(Path.of(filePath));
        String withoutComments = code.replaceAll("//.*", "").replace("(?s)/\\*.*?\\*/", "");

        // return "/문자열" 꼴 찾기
        Pattern pattern = Pattern.compile("return\\s+\"(/.*?)\";");
        // return\s+"(/.*?)"; 형태
        Matcher matcher = pattern.matcher(withoutComments);

        boolean hasViolation = false; // true로 셋 되지 않으면 괜찮은 상황

        while (matcher.find()) {
            String matched = matcher.group(1);
            System.out.println("잘못된 반환 경로 발견: " + matched);
            hasViolation = true;
        }

        if (hasViolation) {
            System.out.println("\n컴파일 실패: 슬래시(/)로 시작하는 뷰 반환이 존재합니다. 해당 부분을 수정해주세요.");
        } else {
            System.out.println("컴파일 성공: 모든 뷰 반환이 안전합니다.");
        }
    }
}
