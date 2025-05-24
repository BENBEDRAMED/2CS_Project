import Invoice from '../models/Invoice.js';
import staticDb from '../staticDb.js';
import PDFDocument from 'pdfkit';
import fs from 'fs';

export const generateInvoice = async (commandeId) => {
  // Get payment and command details
  const command = staticDb.getCommand(commandeId);
  const invoiceNumber = `INV-${Date.now()}-${commandeId}`;

  // Create invoice document
  const invoice = new Invoice({
    commande_id: commandeId,
    merchant_id: command.merchant_id,
    livreur_id: command.livreur_id,
    merchant_amount: command.merchant_amount,
    delivery_amount: command.delivery_amount,
    total_amount: command.total_amount,
    invoice_number: invoiceNumber,
    payment_status: 'paid'
  });

  // Generate PDF
  const doc = new PDFDocument();
  const filePath = `./invoices/${invoiceNumber}.pdf`;
  
  // Pipe PDF to file
  doc.pipe(fs.createWriteStream(filePath));
  
  // Add PDF content
  doc.fontSize(20).text('Payment Invoice', { align: 'center' });
  doc.moveDown();
  
  // Invoice details
  doc.fontSize(12)
    .text(`Invoice Number: ${invoiceNumber}`)
    .text(`Date: ${new Date().toLocaleDateString()}`)
    .moveDown();

  // Merchant/Livreur info
  doc.text(`Merchant ID: ${command.merchant_id}`)
    .text(`Livreur ID: ${command.livreur_id}`)
    .moveDown();

  // Payment breakdown
  doc.font('Helvetica-Bold').text('Payment Breakdown:');
  doc.font('Helvetica')
    .text(`- Merchant Amount: ${command.merchant_amount} DZD`)
    .text(`- Delivery Fee: ${command.delivery_amount} DZD`)
    .text(`- Total Amount: ${command.total_amount} DZD`)
    .moveDown();

  // Footer
  doc.fontSize(10)
    .text('Payment Status: PAID', { align: 'right' })
    .text('Thank you for your business!', { align: 'center' });

  doc.end();

  // Save to database
  invoice.pdf_url = filePath;
  await invoice.save();
  
  return invoice;
};