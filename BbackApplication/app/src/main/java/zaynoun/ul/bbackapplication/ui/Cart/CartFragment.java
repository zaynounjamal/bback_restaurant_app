package zaynoun.ul.bbackapplication.ui.Cart;

import static android.text.TextUtils.isEmpty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.ViewModel.CartViewModel;
import zaynoun.ul.bbackapplication.activities.CheckoutActivity;
import zaynoun.ul.bbackapplication.adapters.CartAdapter;
import zaynoun.ul.bbackapplication.models.CartModel;

public class CartFragment extends Fragment implements CartAdapter.CartListener {

    private CartViewModel cartViewModel;
    private CartAdapter cartAdapter;
    private TextView totalPriceView, emptyTextView;
    private Button checkoutButton;
    private ImageView clearCartButton;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
    }

    private void setupObservers() {
        cartViewModel.getCartItems().observe(getViewLifecycleOwner(), items -> {
            cartAdapter.updateItems(items);
            updateUI(items);
            // Remove manual saveCart() here - handled by ViewModel
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        totalPriceView = view.findViewById(R.id.total_cart);
        emptyTextView = view.findViewById(R.id.empty_text_view);
        checkoutButton = view.findViewById(R.id.make_order);
        clearCartButton = view.findViewById(R.id.clearAll);
        clearCartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cartViewModel.clearCart();
                Toast.makeText(getContext(), "Cart cleared", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.cart_rec);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartAdapter = new CartAdapter(this);
        recyclerView.setAdapter(cartAdapter);

        setupObservers();
        setupCheckout();

        return view;
    }


    private void updateUI(List<CartModel> items) {
        emptyTextView.setVisibility(items.isEmpty() ? View.VISIBLE : View.GONE);
        checkoutButton.setEnabled(!items.isEmpty());
        clearCartButton.setVisibility(items.isEmpty() ? View.GONE : View.VISIBLE);
        double total = 0;
        for (CartModel item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        totalPriceView.setText(String.format("Total: %.2f", total));
    }

    private void setupCheckout() {
        checkoutButton.setOnClickListener(v -> {
            List<CartModel> currentItems = cartViewModel.getCartItems().getValue();
            if (currentItems == null || currentItems.isEmpty()) {
                Toast.makeText(getContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
            } else {
                double total = calculateTotal(currentItems);
                Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                intent.putExtra("total", total); // Key is lowercase "total"
                startActivity(intent);
            }
        });
    }
    private double calculateTotal(List<CartModel> items) {
        if (items == null) return 0.0;
        double total = 0;
        for (CartModel item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    // In CartFragment.java
    @Override
    public void onUpdateQuantity(int position, int newQuantity) {
        cartViewModel.updateQuantity(position, newQuantity);
        cartAdapter.notifyItemChanged(position); // Ensure the view is updated
    }


    @Override
    public void onRemoveItem(int position) {

        cartViewModel.removeItem(position);
    }
}