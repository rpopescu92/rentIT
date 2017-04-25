package com.rentIT.validation;

import com.rentIT.dto.PropertyDto;
import com.rentIT.exception.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Qualifier(value = "propertyValidator")
public class PropertyValidator implements Validator<PropertyDto> {

    @Override
    public boolean isValid(PropertyDto objectToValidate) {
        if (StringUtils.isEmpty(objectToValidate.getAddress().getStreetName()) || StringUtils.isEmpty(objectToValidate.getAddress().getStreetNumber())
                || StringUtils.isEmpty(objectToValidate.getTitle())
                || StringUtils.isEmpty(objectToValidate.getConstructionYear())) {
            throw new InvalidPropertyException("Invalid fields. Address cannot be empty");
        }
        return true;
    }
}
