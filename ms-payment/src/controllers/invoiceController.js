import Invoice from '../models/Invoice.js';
import staticDb from '../staticDb.js';



export const getInvoice = async (req, res) => {
  try {
    const invoice = await Invoice.findOne({ 
      commande_id: req.params.commande_id 
    });
    
    if (!invoice) return res.status(404).json({ error: 'Invoice not found' });
    
    res.json(invoice);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};

export const downloadInvoice = async (req, res) => {
  try {
    const invoice = await Invoice.findOne({
      commande_id: req.params.commande_id
    });
    
    if (!invoice || !invoice.pdf_url) {
      return res.status(404).json({ error: 'Invoice PDF not found' });
    }
    
    res.download(invoice.pdf_url);
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
};