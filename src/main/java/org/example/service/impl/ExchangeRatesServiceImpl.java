package org.example.service.impl;

import org.example.client.CbrClient;
import org.example.exception.ServiceException;
import org.example.service.ExchangeRatesService;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
@Component
@Service
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    private static final String USD_XPATH= "/ValCurs//Valute[@ID='R01235']/Value";
    private static final String EUR_XPATH= "/ValCurs//Valute[@ID='R01239']/Value";
    private static final String THB_XPATH= "/ValCurs//Valute[@ID='R01675']/Value";
    @Autowired
    private CbrClient client;

    @Override
    public String getUSDExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml,USD_XPATH);
    }

    @Override
    public String getEURExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml,EUR_XPATH);

    }

    @Override
    public String getTHBExchangeRate() throws ServiceException {
        var xml = client.getCurrencyRatesXML();
        return extractCurrencyValueFromXML(xml,THB_XPATH);
    }
    private  static String extractCurrencyValueFromXML(String xml, String xpathExpression) throws ServiceException {
        var source = new InputSource(new StringReader(xml));
        try{
            var xpath = XPathFactory.newInstance().newXPath();
            var document = (Document)xpath.evaluate("/", source, XPathConstants.NODE);
            return xpath.evaluate(xpathExpression, document);
        }catch (XPathExpressionException e){
            throw new ServiceException("Не удалось распознать XML", e);
        }
    }
}
