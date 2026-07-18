package org.jobportal.portal.company.service;

import org.jobportal.portal.dto.CompanyDto;
import org.jobportal.portal.entity.Company;
import org.jobportal.portal.repository.CompanyRepository;

import java.util.List;

public interface ICompanyService {


    List<CompanyDto> getAllCompanies();
}
