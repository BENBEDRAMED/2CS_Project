import * as invoiceController from '../controllers/invoiceController.js';
import express from 'express';
import * as paymentController from '../controllers/paymentController.js';

const router = express.Router();


router.post('/initiate', paymentController.initiatePayment); // to create payment 
router.post('/webhook', paymentController.mockWebhook);  // webhook 
router.get('/status/:commande_id', paymentController.checkStatus); // verify (get )


router.get('/invoice/:commande_id', invoiceController.getInvoice);
router.get('/invoice/pdf/:commande_id', invoiceController.downloadInvoice);
export default router;