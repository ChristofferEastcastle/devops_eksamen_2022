package no.shoppifly;

import io.micrometer.core.instrument.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.ToDoubleFunction;

@RestController()
public class ShoppingCartController {

    private Counter cartCounter;
    private Counter checkouts;
    private Meter cartsValue;

    @Autowired
    private final CartService cartService;

    @Autowired
    ShoppingCartController(MeterRegistry meterRegistry, CartService cartService) {
        this.cartService = cartService;
        String prefix = "SHOPPING_CART_CONTROLLER";
        this.cartCounter = meterRegistry.counter(prefix + ".carts");
        this.checkouts = meterRegistry.counter(prefix + ".checkouts");
        this.cartsValue = Gauge.builder(prefix + ".cartsvalue", cartService, CartService::total).register(meterRegistry);
    }

    public ShoppingCartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping(path = "/cart/{id}")
    public Cart getCart(@PathVariable String id) {
        return cartService.getCart(id);
    }

    /**
     * Checks out a shopping cart. Removes the cart, and returns an order ID
     *
     * @return an order ID
     */
    @PostMapping(path = "/cart/checkout")
    public String checkout(@RequestBody Cart cart) {
        cartCounter.increment(-1);
        checkouts.increment();
        return cartService.checkout(cart);
    }

    /**
     * Updates a shopping cart, replacing it's contents if it already exists. If no cart exists (id is null)
     * a new cart is created.
     *
     * @return the updated cart
     */
    @PostMapping(path = "/cart")
    public Cart updateCart(@RequestBody Cart cart) {
        cartCounter.increment();
        return cartService.update(cart);
    }

    /**
     * return all cart IDs
     *
     * @return
     */
    @GetMapping(path = "/carts")
    public List<String> getAllCarts() {
        return cartService.getAllsCarts();
    }


}