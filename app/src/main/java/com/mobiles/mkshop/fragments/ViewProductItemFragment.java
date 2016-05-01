package com.mobiles.mkshop.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mobiles.mkshop.R;
import com.mobiles.mkshop.pojos.models.Product;
import com.mobiles.mkshop.slide.SlidingUpPanelLayout;

public class ViewProductItemFragment extends Fragment {

    Long id;
    ViewPager viewPager;
    SlidingUpPanelLayout mLayout;
    Product p;

    Button buyButton;

    EditText brand, model, sim, screenSize, displayType, platform, iMemory, eMemory, fCamera, rCamera, price, battery;
//    EditText bluetooth;

    public static ViewProductItemFragment newInstance(Long param1) {
        ViewProductItemFragment fragment = new ViewProductItemFragment();
        Bundle args = new Bundle();
        args.putLong("id", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewProductItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_view_product_item, container, false);
        viewPager = (ViewPager) viewGroup.findViewById(R.id.pager);

        brand = (EditText) viewGroup.findViewById(R.id.brand);
        model = (EditText) viewGroup.findViewById(R.id.model);
        sim = (EditText) viewGroup.findViewById(R.id.sim);
        screenSize = (EditText) viewGroup.findViewById(R.id.screenSize);
        displayType = (EditText) viewGroup.findViewById(R.id.displayType);
        platform = (EditText) viewGroup.findViewById(R.id.platform);
        iMemory = (EditText) viewGroup.findViewById(R.id.iMemory);
        eMemory = (EditText) viewGroup.findViewById(R.id.eMemory);
        fCamera = (EditText) viewGroup.findViewById(R.id.fCamera);
        rCamera = (EditText) viewGroup.findViewById(R.id.rCamera);
//        bluetooth = (EditText) viewGroup.findViewById(R.id.bluetooth);
        price = (EditText) viewGroup.findViewById(R.id.price);
        battery = (EditText) viewGroup.findViewById(R.id.battery);

        buyButton = (Button) viewGroup.findViewById(R.id.submit);
        mLayout = (SlidingUpPanelLayout) viewGroup.findViewById(R.id.sliding_layout);


        Product product = Product.findById(Product.class, id);
        p = product;

        brand.setText(product.getBrand());
        model.setText(product.getModel());
        sim.setText(product.getSim());
        screenSize.setText(product.getScreenSize());
        displayType.setText(product.getDisplayType());
        platform.setText(product.getOs());
        iMemory.setText(product.getiMemory());
        eMemory.setText(product.geteMemory());
        fCamera.setText(product.getfCamera());
        rCamera.setText(product.getbCamera());
        //  bluetooth.setText(product.getBluetooth()+"/"+product.getWlan()+"/"+product.getNfc()+"/"+product.getInfrared()+"/"+product.getRadio());
        price.setText(String.valueOf(product.getPrice()));
        battery.setText(product.getBattery());

    /*
    * Get the image for the products and than uncomment it !!
    *
    * */
        //                ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(), prodcutSales.get(0).getPath());
//                viewPager.setAdapter(adapter);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleFragment fragment = SaleFragment.newInstance(p.getBrand(), p.getModel());
                getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });


        return viewGroup;
    }


}
