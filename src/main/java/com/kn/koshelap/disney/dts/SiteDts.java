package com.kn.koshelap.disney.dts;

import java.util.List;

import com.kn.koshelap.disney.dto.ComponentDto;
import com.kn.koshelap.disney.dto.SiteDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SiteDts {
    private SiteDto siteDto;
    private List<ComponentDto> components;

}
