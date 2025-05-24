// src/services/paymentService.js
import { getCommandeById, updateCommandePaymentStatus } from '../clients/commandeClient.js';
import { getUserById, verifyCommercant } from '../clients/userClient.js';
import Payment from '../models/payment.js';
import { generateInvoice } from './invoiceService.js';





export const createPayment = async (commandeId) => {
  const commande = await getCommandeById(commandeId);

  const splits = [];

  for (const article of commande.articles) {
    const produit = article.produit;
    const merchantId = produit?.id_commercant;

    if (!merchantId) {
      console.warn(`Missing merchant ID for article ID ${article.id}`);
      continue;
    }

    // Verify merchant
    const isValidMerchant = await verifyCommercant(merchantId);
    if (!isValidMerchant) {
      throw new Error(`Invalid merchant ID: ${merchantId}`);
    }

    const articleTotal = article.prixTotal;
    const deliveryFee = articleTotal * 0.2;
    const merchantAmount = articleTotal;

    // Add merchant split for this article
    splits.push({
      article_id: article.id,
      recipient_type: "merchant",
      recipient_id: merchantId.toString(),
      amount: parseFloat(merchantAmount.toFixed(2)),
      status: "pending"
    });

    // Add delivery split for this article
    splits.push({
      article_id: article.id,
      recipient_type: "livreur",
      recipient_id: "3", // TODO: Replace with actual delivery person ID
      amount: parseFloat(deliveryFee.toFixed(2)),
      status: "pending"
    });
  }

  const paymentData = {
    commande_id: commandeId,
    total_amount: commande.montantTotal,
    status: "pending",
    splits,
    checkout_url: `https://mock-checkout.com/${commandeId}`
  };

  const payment = await Payment.create(paymentData);
  return payment;
};

export const handlePaymentSuccess = async (commandeId) => {
  // Update payment status
  const updatedPayment = await Payment.findOneAndUpdate(
    { commande_id: commandeId },
    {
      status: 'paid',
      'splits.0.status': 'paid',
      'splits.1.status': 'paid'
    },
    { new: true }
  );

  if (!updatedPayment) {
    throw new Error('Payment not found');
  }

  // Update commande service
  await updateCommandePaymentStatus(commandeId, 'PAID');

  // Generate invoice
  const invoice = await generateInvoice(commandeId);
  
  return { payment: updatedPayment, invoice };
};