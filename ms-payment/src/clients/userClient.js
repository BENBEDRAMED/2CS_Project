// src/clients/userClient.js
import axios from 'axios';
import dotenv from 'dotenv';
dotenv.config();

const USER_SERVICE_URL = process.env.USER_SERVICE_URL || 'http://localhost:8083';

export const getUserById = async (userId) => {
  try {
    const response = await axios.get(`${USER_SERVICE_URL}/api/utilisateurs/${userId}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching user:', error.response?.data);
    throw new Error('Failed to fetch user details');
  }
};

export const verifyCommercant = async (commercantId) => {
  if (!commercantId) {
    console.error('Invalid commercantId:', commercantId);
    return false;
  }

  try {
    const response = await axios.get(`${USER_SERVICE_URL}/api/utilisateurs/commercant/${commercantId}/exists`);
    return response.data;
  } catch (error) {
    console.error('Error verifying commercant:', error.response?.data);
    return false;
  }
};