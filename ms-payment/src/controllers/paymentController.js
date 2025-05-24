import * as paymentService from '../services/paymentService.js';
import Payment from '../models/payment.js';

export const initiatePayment = async (req, res) => {
  try {
    const { commande_id } = req.body;
    console.log("id",commande_id);
    if (!commande_id) {
      return res.status(400).json({ error: "commande_id is required" });
    }

    const payment = await paymentService.createPayment(commande_id);
    res.json({
      success: true,
      checkout_url: payment.checkout_url
    });
  } catch (error) {
    res.status(500).json({ 
      success: false,
      error: error.message 
    });
  }
};

export const mockWebhook = async (req, res) => {
  try {
    const { commande_id } = req.body;
  
    const payment = await paymentService.handlePaymentSuccess(commande_id);
    res.json({ success: true, payment });
  } catch (error) {
    res.status(400).json({
      success: false,
      error: error.message
    });
  }
};

export const checkStatus = async (req, res) => {
  try {
    const commande_id = req.params.commande_id.trim();
    const payment = await Payment.findOne({ commande_id });

    if (!payment) {
      return res.status(404).json({
        error: "Payment not found",
        requestedId: commande_id
      });
    }

    res.json(payment);
  } catch (error) {
    res.status(500).json({
      success: false,
      error: error.message
    });
  }
};