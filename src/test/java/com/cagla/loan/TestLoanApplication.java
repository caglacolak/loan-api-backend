package com.cagla.loan;

import org.springframework.boot.SpringApplication;

public class TestLoanApplication {

	public static void main(String[] args) {
		SpringApplication.from(LoanApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
