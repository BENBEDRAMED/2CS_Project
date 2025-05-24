// test.js
import staticDb from './staticDb.js';

// Simulate a client request
async function testFlow() {
  // 1. Get existing command
  const command = staticDb.getCommand("CMD001");
  
  // 2. Initiate payment
  const payment = await paymentService.createPayment(command.id);
  console.log("Payment created:", payment);
  
  // 3. Simulate successful payment webhook
  await paymentService.handlePaymentSuccess(command.id);
  
  // 4. Check results
  console.log("Updated payment:", staticDb.getPayment(payment.id));
  console.log("Updated command:", staticDb.getCommand(command.id));
}

testFlow();