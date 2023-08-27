package com.example.restapi.automatic.model;

import com.example.restapi.automatic.model.marker.AdminValidationGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class RequestParams {
    @NotNull(groups = {AdminValidationGroup.class})
    @NotEmpty(groups = {AdminValidationGroup.class})
    private Map<String, Object> params;

    public Set<String> getKeys() {
        return params.keySet();
    }

    public List<String> getValueOfKey(String key) {
        String[] orDefault = (String[]) params.getOrDefault(key, new String[]{});
        return Arrays.asList(orDefault);
    }
}
