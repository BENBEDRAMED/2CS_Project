import mongoose from 'mongoose';

const invoiceSchema = new mongoose.Schema({
  commande_id: {
    type: String,
    required: true,
    unique: true
  },
  merchant_id: String,
  livreur_id: String,
  merchant_amount: Number,
  delivery_amount: Number,
  total_amount: Number,
  payment_date: {
    type: Date,
    default: Date.now
  },
  invoice_number: String,
  payment_status: {
    type: String,
    enum: ['paid', 'pending', 'failed'],
    default: 'pending'
  }
}, { timestamps: true });

export default mongoose.model('Invoice', invoiceSchema);