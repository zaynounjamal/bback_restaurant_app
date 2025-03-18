package zaynoun.ul.bbackapplication.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import zaynoun.ul.bbackapplication.R;
import zaynoun.ul.bbackapplication.models.HomeHorModel;
import zaynoun.ul.bbackapplication.models.HomeVerModel;

public class HomeHorAdapter extends RecyclerView.Adapter<HomeHorAdapter.ViewHolder> {
    private final UpdateVerticalRec updateVerticalRec;
    private final ArrayList<HomeHorModel> list;
    private final Activity activity;
    private int selectedIndex = 0;
    private int selectedPosition = 0;
    private boolean isFirstLoad = true;

    public HomeHorAdapter(ArrayList<HomeHorModel> list, UpdateVerticalRec updateVerticalRec, Activity activity) {
        this.list = list;
        this.updateVerticalRec = updateVerticalRec;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_horizontal_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        HomeHorModel model = list.get(position);

        // Load image from resource using Glide
        Glide.with(activity)
                .load(model.getImageResourceId())
                .into(holder.imageView);

        holder.name.setText(model.getName());

        if (isFirstLoad && position == 0) {
            holder.cardView.setBackgroundResource(R.drawable.change_bg);
            updateVerticalRec.callBack(position, getCategoryItems("Pizza"));
            isFirstLoad = false;
        }

        holder.cardView.setOnClickListener(v -> {
            selectedIndex = position;
            notifyDataSetChanged();
            updateVerticalRec.callBack(position, model.getName()); // Pass the category name
        });

        holder.cardView.setBackgroundResource(
                selectedIndex == position ? R.drawable.change_bg : R.drawable.default_bg
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private ArrayList<HomeVerModel> getCategoryItems(String category) {
        ArrayList<HomeVerModel> items = new ArrayList<>();
        switch (category) {
            case "Pizza":
                addItems(items, "Pizza",
                        new String[]{
                                "https://upload.wikimedia.org/wikipedia/commons/a/a3/Eq_it-na_pizza-margherita_sep2005_sml.jpg",
                                "https://upload.wikimedia.org/wikipedia/commons/c/c8/Pizza_Margherita_stu_spivack.jpg",
                                "https://upload.wikimedia.org/wikipedia/commons/6/64/NYPizzaPie.jpg"
                        },
                        new String[]{"Margherita", "Pepperoni", "New York Style"},
                        new double[]{12.99, 14.99, 15.99});
                break;
            case "HamBurger":
                addItems(items, "HamBurger",
                        new String[]{
                                "https://upload.wikimedia.org/wikipedia/commons/4/47/Hamburger_%28black_bg%29.jpg",
                                "https://upload.wikimedia.org/wikipedia/commons/0/0b/RedDot_Burger.jpg",
                                "https://cdn.pixabay.com/photo/2016/03/05/19/02/hamburger-1238246_1280.jpg"
                        },
                        new String[]{"Classic Burger", "Cheese Burger", "Bacon Burger"},
                        new double[]{8.99, 9.99, 10.99});
                break;
            case "Fries":
                addItems(items, "Fries",
                        new String[]{
                                "https://upload.wikimedia.org/wikipedia/commons/8/83/French_Fries.JPG",
                                "https://upload.wikimedia.org/wikipedia/commons/6/67/Fries_2.jpg",
                                "https://upload.wikimedia.org/wikipedia/commons/f/ff/French_Fries_%2815588498304%29.jpg"
                        },
                        new String[]{"Regular Fries", "Cheese Fries", "Curly Fries"},
                        new double[]{3.99, 5.99, 4.99});
                break;
            case "Sandwich":
                addItems(items, "Sandwich",
                        new String[]{
                                "https://tse1.mm.bing.net/th?id=OIP.b4IiO3NLO8ISVTk0SyRhPQHaFj&w=355&h=355&c=7", // Turkey sandwich
                                "https://tse4.mm.bing.net/th?id=OIP.mLFjnOCZMm_p5y-NSkZX2AHaLH&w=474&h=474&c=7", // Club sandwich
                                "https://tse3.mm.bing.net/th?id=OIP.psD8eStPnGMYN1f3BCGqAQHaFP&w=335&h=335&c=7"  // Grilled
                        },
                        new String[]{"Turkey Sandwich", "Club Sandwich", "Grilled Cheese"},
                        new double[]{6.99, 5.99, 7.99});
                break;
            case "Mashawi":
                addItems(items, "Mashawi",
                        new String[]{
                                "https://tse4.mm.bing.net/th?id=OIP.cI8j0v5BQrvxVYEIvXahQwHaE7&w=315&h=315&c=7", // Chicken skewers
                                "https://tse4.mm.bing.net/th?id=OIP.aM8JIb_s9hHSLU-nqjzOVwHaFj&w=355&h=355&c=7", // Beef kebab
                                "https://tse4.mm.bing.net/th?id=OIP.pHNjQTnORP53_dD2YxIOZgHaE8&w=316&h=316&c=7"
                        },
                        new String[]{"Chicken Skewers", "Beef Kebab", "Mixed Grill"},
                        new double[]{10.99, 12.99, 11.99});
                break;
        }
        return items;
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }

    private void addItems(ArrayList<HomeVerModel> items, String category,
                          String[] imageUrls, String[] names, double[] prices) {
        for (int i = 0; i < names.length; i++) {
            items.add(new HomeVerModel(
                    category + "_" + (i + 1),
                    imageUrls[i],
                    names[i],
                    "10:00 - 23:00",
                    "4.9",
                    prices[i],
                    false
            ));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pizza_category);
            name = itemView.findViewById(R.id.pizza_category_text);
            cardView = itemView.findViewById(R.id.CardView);
        }
    }
}