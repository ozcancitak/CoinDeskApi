package com.coindesk.digitalcurrency.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.Serializable;

/**
 * {@link Data} @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor in one @Data annotation
 * {@link JsonIgnoreProperties} Annotation ignores processing of JSON properties
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRateData implements Serializable{

	private String code;
		
	@JsonProperty(value="rate")	
	private String rate;

	@JsonProperty(value="rate_float")
	@JsonDeserialize(as=Double.class)
	private Double currentRate;
	
	private String minRateDate;
    private Double minRate;
    private String maxRateDate;
    private Double maxRate;
    private String startDate;
    private String endDate;
    private boolean currencyNotFound;
}
