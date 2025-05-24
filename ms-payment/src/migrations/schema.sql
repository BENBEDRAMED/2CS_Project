-- schema.sql
-- Enable UUID extension if you want to use UUIDs instead of custom IDs
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create commands table
CREATE TABLE commands (
  id VARCHAR(36) PRIMARY KEY DEFAULT uuid_generate_v4(),
  merchant_id VARCHAR(255) NOT NULL,
  livreur_id VARCHAR(255) NOT NULL,
  merchant_amount INTEGER NOT NULL CHECK (merchant_amount >= 0),
  delivery_amount INTEGER NOT NULL CHECK (delivery_amount >= 0),
  total_amount INTEGER NOT NULL CHECK (total_amount >= 0),
  status VARCHAR(50) NOT NULL DEFAULT 'pending',
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create payments table
CREATE TABLE payments (
  id VARCHAR(36) PRIMARY KEY DEFAULT uuid_generate_v4(),
  commande_id VARCHAR(36) REFERENCES commands(id) ON DELETE CASCADE,
  total_amount INTEGER NOT NULL CHECK (total_amount >= 0),
  status VARCHAR(50) NOT NULL DEFAULT 'pending',
  splits JSONB NOT NULL,
  checkout_url VARCHAR(255),
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_commands_status ON commands(status);
CREATE INDEX idx_payments_commande_id ON payments(commande_id);
CREATE INDEX idx_payments_status ON payments(status);

-- Insert sample data (optional)
INSERT INTO commands (id, merchant_id, livreur_id, merchant_amount, delivery_amount, total_amount, status)
VALUES 
  ('CMD001', 'MER123', 'LIV456', 8000, 2000, 10000, 'pending'),
  ('CMD002', 'MER1234', 'LIV4567', 8000, 2000, 10000, 'pending');