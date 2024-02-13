package uz.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApiResponse {
    private int status_code;
    private boolean success;
    private Object content;

}
