package com.example.demo.model;

public record ApplicationDetails(String appName, long clientOrOktaId, String oktaDomain, String signOnMode,
		String status, String appUrl) {

}
