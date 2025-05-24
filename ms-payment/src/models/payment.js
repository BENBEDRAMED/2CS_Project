// src/models/payment.js
import mongoose from 'mongoose';

const paymentSchema = new mongoose.Schema({
  commande_id: {
    type: String,
    required: true,
    unique: true
  },
  total_amount: {
    type: Number,
    required: true
  },
  status: {
    type: String,
    enum: ['pending', 'paid', 'failed'],
    default: 'pending'
  },
  splits: [{
    recipient_type: {
      type: String,
      enum: ['merchant', 'livreur'],
      required: true
    },
    recipient_id: {
      type: String,
      required: true
    },
    amount: {
      type: Number,
      required: true
    },
    status: {
      type: String,
      enum: ['pending', 'paid', 'failed'],
      default: 'pending'
    }
  }],
  checkout_url: String,
  metadata: {
    client_id: String,
    merchant_id: String,
    livreur_id: String
  }
}, {
  timestamps: true
});

export default mongoose.model('Payment', paymentSchema);