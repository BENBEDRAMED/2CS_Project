// src/clients/commandeClient.js
import axios from 'axios';
import dotenv from 'dotenv';
dotenv.config();


const COMMANDE_SERVICE_URL = 'http://localhost:8082';

export const getCommandeById = async (commandeId) => {
  try {
    const response = await axios.get(`${COMMANDE_SERVICE_URL}/api/commandes/${commandeId}`);
    console.log('Commande fetched:', response.data); // response data
    return response.data;
  } catch (error) {
    console.error('Full error object:', error);
    if (error.response) {
      // Server responded with a status other than 2xx
      console.error('Error response from commande service:', {
        status: error.response.status,
        data: error.response.data
      });
    } else if (error.request) {
      // Request was made but no response
      console.error('No response from commande service:', error.request);
    } else {
      // Something else happened
      console.error('Error setting up request:', error.message);
    }
    throw new Error('Failed to fetch commande details');
  }

};

export const updateCommandePaymentStatus = async (commandeId, status) => {
  try {
    await axios.patch(`${COMMANDE_SERVICE_URL}/api/commandes/${commandeId}/payment-status`, {
      paymentStatus: status
    });
  } catch (error) {
    console.error('Error updating commande status:', error.response?.data);
    throw new Error('Failed to update commande status');
  }
};