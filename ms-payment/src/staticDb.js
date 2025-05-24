// src/staticDb.js
const database = {
    commands: [{
      id: "CMD001",
      merchant_id: "MER123",
      livreur_id: "LIV456",
      merchant_amount: 8000,
      delivery_amount: 2000,
      total_amount: 10000,
      status: "pending"
    },
    {
        id: "CMD002",
        merchant_id: "MER1234",
        livreur_id: "LIV4567",
        merchant_amount: 8000,
        delivery_amount: 2000,
        total_amount: 10000,
        status: "pending"
      
}],
    payments: []
  };
  
  export default {
    get commands() { return [...database.commands] }, // Return copies
    get payments() { return [...database.payments] }, // Return copies
    
    getCommand(id) {
      return database.commands.find(c => c.id === id);
    },
    
    createPayment(payment) {
      // Deep clone the payment object
      const newPayment = JSON.parse(JSON.stringify(payment));
      database.payments.push(newPayment);
      return newPayment;
    },
    
    getPayment(commandeId) {
        const cleanId = String(commandeId).trim();
        return database.payments.find(p => 
            String(p.commande_id).trim() === cleanId
        );
    },
    updatePayment(commandeId, updates) {
        const index = database.payments.findIndex(p => p.commande_id === commandeId);
        if (index !== -1) {
          database.payments[index] = {
            ...database.payments[index],
            ...updates,
            updated_at: new Date()
          };
          return true;
        }
        return false;
      }
  };