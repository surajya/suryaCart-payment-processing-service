package com.payment.service.interfaces;

import com.payment.dto.stripe.StripeEventDTO;

public interface StripeWebhookService {

	public String processEvent(StripeEventDTO stripeEventDTO);

}
