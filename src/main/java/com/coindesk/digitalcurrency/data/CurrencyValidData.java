package com.coindesk.digitalcurrency.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;


/**
 * {@link Data} @Getter, @Setter, @ToString, @EqualsAndHashCode and @RequiredArgsConstructor in one @Data annotation
 * {@link JsonIgnoreProperties} Annotation ignores processing of JSON properties
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyValidData implements Serializable{

	private String currency;
	private String country;
	
}
