package com.tabber.tabby.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class EmailHistoryResponse {

    @JsonProperty("page_no")
    private Integer pageNo;

    @JsonProperty("items_per_page")
    private Integer itemsPerPage;

    @JsonProperty("total_items")
    private Integer totalItems;

    @JsonProperty("mail_history")
    private List<JSONObject> mailHistory;

}
