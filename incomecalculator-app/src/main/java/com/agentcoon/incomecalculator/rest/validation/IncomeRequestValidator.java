package com.agentcoon.incomecalculator.rest.validation;

import com.agentcoon.incomecalculator.api.IncomeRequestDto;

public class IncomeRequestValidator {

    public static void validate(IncomeRequestDto dto) throws IncomeValidationException {
        if (dto.getAmount().signum() < 0) {
            throw new IncomeValidationException("Amount can't be less than 0.");
        }
    }
}
