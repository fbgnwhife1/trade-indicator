package tradeindicatorservice.tradeindicator.Service.BSISocket;

import lombok.Data;

@Data
public class BSI_RBIDto {
    private String market;
    private Double score;
    private Boolean go;
}
