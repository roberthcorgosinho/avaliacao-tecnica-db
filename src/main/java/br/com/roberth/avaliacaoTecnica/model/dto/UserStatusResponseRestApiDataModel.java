package br.com.roberth.avaliacaoTecnica.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserStatusResponseRestApiDataModel {
	
	@JsonProperty(value="id", defaultValue="0")
	private Long id;
	
	@JsonProperty(value="status", defaultValue="ABLE_TO_VOTE")
	private String status;

}
