package tradeindicatorservice.tradeindicator.Service.BSISocket;


import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/*
    ref.
    https://sas-study.tistory.com/432
 */

@RequiredArgsConstructor
public final class WsListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private final Gson gson = new Gson();
    private String json;
    private Conclusion conclusion;
    private final RabbitTemplate rabbitTemplate;
    private static final String topicExchangeName = "bsi.exchange";

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.printf("Socket Closed : %s / %s\r\n", code, reason);
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.printf("Socket Closing : %s / %s\n", code, reason);
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        webSocket.cancel();
    }

    @SneakyThrows
    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
        System.out.println("Socket Error : " + t.getMessage() + " socket Reconnecting...");

        webSocket.cancel();
        webSocket.send(getParameter());
    }


    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        JsonNode jsonNode = gson.fromJson(text, JsonNode.class);
        System.out.println(jsonNode.toPrettyString());
    }

    @SneakyThrows
    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
        switch(conclusion) {
            case orderbook:
                OrderBookResult orderBookResult = gson.fromJson(bytes.string(StandardCharsets.UTF_8), OrderBookResult.class);
                BSI_RBIDto bsi_rbiDto = orderBookResult.toResult();
                rabbitTemplate.convertAndSend(topicExchangeName, "q.bsi.*", bsi_rbiDto);
                break;
            default:
                throw new RuntimeException("지원하지 않는 웹소켓 조회 유형입니다. : " + conclusion.getType());
        }
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        webSocket.send(getParameter());
    }

    public void setParameter(String UUID, Conclusion conclusion, List<String> codes) {
        this.conclusion = conclusion;
        this.json = gson.toJson(List.of(Ticket.of(UUID), Type.of(conclusion, codes)));
    }

    private String getParameter() {
        System.out.println(json);
        return this.json;
    }

    @Data(staticConstructor = "of")
    private static class Ticket {
        private final String ticket;
    }

    @Data(staticConstructor = "of")
    private static class Type {
        private final Conclusion type;
        private final List<String> codes; // market
        private Boolean isOnlySnapshot = false;
        private Boolean isOnlyRealtime = true;
    }
}
