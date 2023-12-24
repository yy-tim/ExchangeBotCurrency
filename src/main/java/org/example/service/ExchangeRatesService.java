package org.example.service;

import org.example.exception.ServiceException;
import org.springframework.stereotype.Service;


public interface ExchangeRatesService {
    String getUSDExchangeRate() throws ServiceException;

    String getEURExchangeRate() throws ServiceException;

    String getTHBExchangeRate() throws ServiceException;
}
