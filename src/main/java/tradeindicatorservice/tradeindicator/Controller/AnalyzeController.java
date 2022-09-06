package tradeindicatorservice.tradeindicator.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tradeindicatorservice.tradeindicator.Response.Message;
import tradeindicatorservice.tradeindicator.Response.StatusEnum;
import tradeindicatorservice.tradeindicator.Service.AnalyzeService;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/indicator-service")
@RequiredArgsConstructor
public class AnalyzeController {

    private final AnalyzeService analyzeService;

    @GetMapping("rsi/{marketId}")
    public ResponseEntity<Message> getRSI(@PathVariable String marketId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = new Message();

        try {
            message.setData(analyzeService.getRsi(marketId));
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            message.setStatus(StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setMessage("OK");
        message.setStatus(StatusEnum.OK);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("rsi/no-cache/{marketId}")
    public ResponseEntity<Message> getRSI_no(@PathVariable String marketId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = new Message();

        try {
            message.setData(analyzeService.no_cache_rsi(marketId));
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            message.setStatus(StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setMessage("OK");
        message.setStatus(StatusEnum.OK);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("bsi/{marketId}")
    public ResponseEntity<Message> getBSI(@PathVariable String marketId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = new Message();

        try {
            message.setData(analyzeService.BSI(marketId));
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            message.setStatus(StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setMessage("OK");
        message.setStatus(StatusEnum.OK);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("fear-and-greed")
    public ResponseEntity<Message> getFnG() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        Message message = new Message();

        try {
            message.setData(analyzeService.getFnG());
        } catch (Exception e) {
            message.setMessage(e.getMessage());
            message.setStatus(StatusEnum.BAD_REQUEST);
            return new ResponseEntity<>(message, headers, HttpStatus.BAD_REQUEST);
        }

        message.setMessage("OK");
        message.setStatus(StatusEnum.OK);
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
