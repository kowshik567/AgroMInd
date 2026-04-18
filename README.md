# 🌾 AgroMind AI: Premium Agriculture Decision Support System

AgroMind AI is a state-of-the-art decision-support system designed to empower farmers and agricultural enthusiasts with AI-driven insights. Built with a focus on high-fidelity aesthetics and intelligence, the dashboard provides a seamless experience for crop selection, weather simulation, and regional data analysis.

---

## ✨ Key Features

### 🧠 1. AI Crop Recommendation & Deep-Dive
- **Decision Engine**: Recommends the best crops based on Soil Type, Season, and Rainfall.
- **Dynamic Radar Charts**: An advanced comparison modal that reveals the "Intelligence" behind each choice. Click any crop card to see a glowing, 5-dimensional Radar Chart comparing:
    - *Soil Compatibility, Rainfall Match, Temperature Resilience, Market Potential, and Yield efficiency.*
- **Dynamic Rank Badges**: Clear visual hierarchy with Gold (#1), Silver (#2), and Bronze (#3) achievement badges.

### 📊 2. Analytics Dashboard (South India Focus)
- **Regional Data Integration**: Includes high-fidelity data for **Andhra Pradesh** and **Telangana**.
- **Interactive Production Trends**: Line and Pie charts with **Hover-to-View Tooltips**. Hovering over data points reveals precise Year and Production (MT) metrics.
- **Animated Insights**: Smooth startup animations for a premium, lightweight feel.

### 🌦️ 3. Weather Simulation & Smart Advice
- **Structured Recommendations**: Provides exactly 4 high-value advice rows based on current weather conditions.
- **Visual Alert System**: High-fidelity icons (⚠️, 🌡️, 🚜, 🌵) to communicate priority status for irrigation and harvesting.

---

## 🎨 Design Aesthetics
- **Emerald Theme**: A professional, deep emerald green navigation system inspired by modern enterprise dashboards.
- **Glassmorphism**: Premium semi-transparent modals with background blur and glowing Emerald accents.
- **Sidebar Excellence**: A unified "Full Side" column layout for maximum UI consistency.

---

## 🛠️ Technology Stack
- **Language**: Java 17+
- **UI Framework**: Java Swing with **FlatLaf (v3.4.1)** for modern theme support.
- **Charts**: Custom painting engine for high-performance interactive Radar and Trend charts.
- **Build System**: Maven (pom.xml)
- **Icons**: Custom SVG-inspired painting and Segoe UI Emoji integration for pixel-perfect scaling.

---

## 🚀 Getting Started

### Prerequisites
- JDK 17 or higher.
- Maven (optional, but recommended).

### Installation & Run
1. Clone the repository.
2. Ensure you have the dependencies in the `lib` folder (specifically `flatlaf-3.4.1.jar`).
3. Run the application using the PowerShell script:
   ```powershell
   .\run.ps1
   ```

---

## ⚖️ AI Model Logic
The recommendations are generated using a heuristic scoring engine that simulates AI weighted-layers:
1. **Soil Match**: Cross-references crop nutrient needs with the input soil type.
2. **Seasonal Weighting**: Prioritizes crops adapted to the current local season (Kharif, Rabi, etc.).
3. **Moisture Compatibility**: Evaluates expected rainfall against the crop's water consumption levels.

---

**Developed with 💚 for Smarter Agriculture.**
   "Precision Decisions, Bountiful Harvests."
