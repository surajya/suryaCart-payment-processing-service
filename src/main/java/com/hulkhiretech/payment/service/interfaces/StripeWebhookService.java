package com.hulkhiretech.payment.service.interfaces;

import com.hulkhiretech.payment.dto.stripe.StripeEventDTO;

public interface StripeWebhookService {
	public String processEvent(StripeEventDTO stripeEventDTO);
}
