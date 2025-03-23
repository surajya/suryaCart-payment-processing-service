package com.hulkhiretech.payment.service.impl;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.hulkhiretech.payment.constant.TransactionStatusEnum;
import com.hulkhiretech.payment.dao.interfaces.TransactionDao;
import com.hulkhiretech.payment.dto.TransactionDTO;
import com.hulkhiretech.payment.dto.stripe.CheckOutSessionCompletedData;
import com.hulkhiretech.payment.dto.stripe.StripeEventDTO;
import com.hulkhiretech.payment.service.interfaces.PaymentStatusService;
import com.hulkhiretech.payment.service.interfaces.StripeWebhookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StripeWebhookServiceImpl implements StripeWebhookService {

    private static final String CHARGE_FAILED = "charge.failed";
	//private static final String PAYMENT_INTENT_PAYMENT_FAILED = "payment_intent.payment_failed";
	private static final String PAYMENT_STATUS_PAID = "paid";
    private static final String COMPLETE = "complete";
    //private static final String CHECKOUT_SESSION_ASYNC_PAYMENT_FAILED = "checkout.session.async_payment_failed";
    private static final String CHECKOUT_SESSION_ASYNC_PAYMENT_SUCCEEDED = "checkout.session.async_payment_succeeded";
    private static final String CHECKOUT_SESSION_COMPLETED = "checkout.session.completed";

    private final Gson gson;
    private final PaymentStatusService paymentStatusService;
    private final TransactionDao transactionDao;

    @Override
    public String processEvent(StripeEventDTO stripeEventDTO) {
        if (stripeEventDTO == null || stripeEventDTO.getType() == null) {
            log.warn("Received null or invalid StripeEventDTO");
            return null;
        }

        log.info("Processing Stripe event of type: {}", stripeEventDTO.getType());

        switch (stripeEventDTO.getType()) {
            case CHECKOUT_SESSION_COMPLETED:
                handleCheckoutSessionCompleted(stripeEventDTO);
                break;

            case CHECKOUT_SESSION_ASYNC_PAYMENT_SUCCEEDED:
                log.info("Handling async payment success event.");
                break;

            case CHARGE_FAILED:
                log.info("Handling async payment failure event.");
                handlePaymentFailed(stripeEventDTO);
                break;

            default:
                log.warn("Unhandled event type: {}", stripeEventDTO.getType());
                break;
        }

        return null;
    }

    private void handleCheckoutSessionCompleted(StripeEventDTO stripeEventDTO) {
        CheckOutSessionCompletedData objData = gson.fromJson(
            stripeEventDTO.getData().getObject(),
            CheckOutSessionCompletedData.class
        );

        log.info("Received CheckOutSessionCompletedData: {}", objData);

        if (COMPLETE.equals(objData.getStatus()) && PAYMENT_STATUS_PAID.equals(objData.getPaymentStatus())) {
            log.info("Payment is completed successfully.");

            String providerReference = objData.getId();
            TransactionDTO txnDTO = transactionDao.getTransactionByProviderReference(providerReference);

            if (txnDTO == null) {
                log.warn("No TransactionDTO found for ProviderReference: {}", providerReference);
                return;
            }

            log.info("TransactionDTO found: {}", txnDTO);

            txnDTO.setTxnStatus(TransactionStatusEnum.SUCCESS.getName());
            paymentStatusService.processStatus(txnDTO);

            log.info("Transaction status updated to SUCCESS.");
        }
    }
    
    private void handlePaymentFailed(StripeEventDTO stripeEventDTO) {
        CheckOutSessionCompletedData objData = gson.fromJson(
            stripeEventDTO.getData().getObject(),
            CheckOutSessionCompletedData.class
        );

        log.warn("Received async payment failure event. Data: {}", objData);

        String providerReference = objData.getId();
        TransactionDTO txnDTO = transactionDao.getTransactionByProviderReference(providerReference);

        if (txnDTO == null) {
            log.warn("No TransactionDTO found for ProviderReference: {}", providerReference);
            return;
        }

        log.info("TransactionDTO found: {}", txnDTO);

        txnDTO.setTxnStatus(TransactionStatusEnum.FAILED.getName());
        paymentStatusService.processStatus(txnDTO);

        log.info("Transaction status updated to FAILED.");
    }
}
