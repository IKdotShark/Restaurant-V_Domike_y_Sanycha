import React, { useRef } from "react";
import Header from "./Header/Header";
import Footer from "./Footer/Footer";
import Logo from "./Logo/Logo";
import ProductSection from "./Products/ProductSection"; // Импортируем новый компонент
import { CartProvider } from "./Cart/CartContext";
import Cart from "./Cart/Cart";
import ReservationMap from "./Table/ReservationMap";

function App() {
  const specialOffersRef = useRef(null);
  const dishesRef = useRef(null);
  const dessertsRef = useRef(null);
  const drinksRef = useRef(null);
  const reservationRef = useRef(null); // Добавлено

  const HEADER_HEIGHT = 128;
  const HEADER_HEIGHT_2 = 188;

  const scrollToSection = (ref) => {
    const sectionPosition = ref.current.offsetTop;
    if (window.innerWidth > 540) {
    window.scrollTo({
      top: sectionPosition - HEADER_HEIGHT,
      behavior: "smooth",
    });
    } else {
      window.scrollTo({
        top: sectionPosition - HEADER_HEIGHT_2,
        behavior: "smooth",
      });
    }
  };

  return (
    <CartProvider>
      <Header
        scrollToSection={scrollToSection}
        refs={{
          specialOffersRef,
          dishesRef,
          dessertsRef,
          drinksRef,
          reservationRef, // Передано в Header
        }}
      />
      <Logo />
      <div className="mainContent">
        <ProductSection
          specialOffersRef={specialOffersRef}
          dishesRef={dishesRef}
          dessertsRef={dessertsRef}
          drinksRef={drinksRef}
        />
        <Cart />
      </div>
      <div ref={reservationRef}>
        <ReservationMap />
      </div>
      <Footer />
    </CartProvider>
  );
}

export default App;
