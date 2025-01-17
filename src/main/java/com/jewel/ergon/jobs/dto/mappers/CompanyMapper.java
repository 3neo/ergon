package com.jewel.ergon.jobs.dto.mappers;


import com.jewel.ergon.jobs.dto.CompanyDto;
import com.jewel.ergon.jobs.model.Company;
import org.mapstruct.Mapper;


//TODO verify the pom maven compiler Plugin paths ?
@Mapper
public interface CompanyMapper {

     CompanyDto toCompanyDto(Company company) ;



}
