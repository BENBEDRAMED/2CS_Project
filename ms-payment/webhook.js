import express from "express";
import bodyParser from "body-parser";
import { verifySignature } from "@chargily/chargily-pay";
import dotenv from "dotenv";

dotenv.config({ path: "./config.env" }); // Load API key from environment variables

const app = express();
const PORT = 4000;

// Middleware to capture raw body as a Buffer for signature verification
app.use(
  bodyParser.json({
    verify: (req, res, buf) => {
      req.rawBody = buf;
    },
  })
);

app.post("/webhook", (req, res) => {
  console.log("🔹 Received Headers:", req.headers); // Debugging

  const signature = req.headers["x-chargily-signature"]; // Get Chargily signature
  console.log("🔹 Received Signature:", signature);

  if (!signature) {
    console.log("❌ Missing signature header");
    return res.status(400).json({ error: "Missing signature header" });
  }

  res.status(200).json({ message: "Webhook received successfully" });
});

app.listen(PORT, () => {
  console.log(`🚀 Webhook server running on port ${PORT}`);
});
