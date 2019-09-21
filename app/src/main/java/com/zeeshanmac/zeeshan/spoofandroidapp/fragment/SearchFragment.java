package com.zeeshanmac.zeeshan.spoofandroidapp.fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zeeshanmac.zeeshan.spoofandroidapp.R;
import com.zeeshanmac.zeeshan.spoofandroidapp.adapter.ChildListAdapter;
import com.zeeshanmac.zeeshan.spoofandroidapp.adapter.SelectedItemsAdapter;
import com.zeeshanmac.zeeshan.spoofandroidapp.model.Items;
import com.zeeshanmac.zeeshan.spoofandroidapp.model.ParentCellData;
import com.zeeshanmac.zeeshan.spoofandroidapp.util.TransparentProgressDialog;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchFragment extends Fragment {

    ChildListAdapter childListAdapter;

    private List<Items> searchedItemsList = new ArrayList<>();

    List<Items> childGroceryItemList = new ArrayList<Items>();

    @BindView(R.id.topRecyclerViewD)
    RecyclerView topRecyclerView;


    @BindView(R.id.searchETD)
    EditText searchET;

    @BindView(R.id.searcIVD)
    ImageView searchIV;

  //  @BindView(R.id.rightIVD)
    //ImageView rightIV;

    TransparentProgressDialog transparentProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e("onCreateView","inside_Search Fragment");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, view);
        transparentProgressDialog = new TransparentProgressDialog(getActivity(), R.drawable.prosessing_icon);

        // preparing list data
        prepareListData();


     //   rightIV.setVisibility(View.INVISIBLE);


//        childListAdapter = new ChildListAdapter(getActivity(), searchedItemsList, null);
//
//        topRecyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
//        topRecyclerView.setLayoutManager(mLayoutManager);
//        topRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
//        topRecyclerView.setItemAnimator(new DefaultItemAnimator());
//
//        topRecyclerView.setAdapter(childListAdapter);



        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchedItemsList.clear();
                transparentProgressDialog.show();
                //Traversal
                boolean flag = false;


                Log.e("Size_",childGroceryItemList.size()+"_zz");
                for (Items item : childGroceryItemList) {
                    String nameStr = item.getItemName().toLowerCase();
                    String queryStr = searchET.getText().toString().toLowerCase();

                    if (nameStr != null && nameStr.contains(queryStr)) {
                        searchedItemsList.add(item);
                        childListAdapter.updateList(searchedItemsList);
                        flag = true;
                        //Process data do whatever you want
                        //System.out.println("Found it!");
                    }
                }
                if (flag == false) {
                    Toast.makeText(getActivity(), "Item Not found!", Toast.LENGTH_SHORT).show();
                    searchedItemsList.clear();
                    childListAdapter.updateList(searchedItemsList);
                }
                transparentProgressDialog.dismiss();

            }
        });

        Log.e("onCreateView","inside_Search Fragment_2");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initRecyclerView(){
        childListAdapter = new ChildListAdapter(getActivity(), childGroceryItemList, null);

        topRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        topRecyclerView.setLayoutManager(mLayoutManager);
        topRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        topRecyclerView.setItemAnimator(new DefaultItemAnimator());

        topRecyclerView.setAdapter(childListAdapter);

    }
    private void prepareListData() {

        childGroceryItemList.add(new Items("1", "Abricot", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Ail", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Ananas", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Artichauts", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Asperges", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Aubergine", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Avocat", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Baies", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Bananes", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Basilic", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Brocoli", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Carottes", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Celeri", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Cerises", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Champignons", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Chou", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Chou-fleur", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Ciboule", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Ciboulette", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Citron", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Concombre", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Coriandre", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Courge", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Courgette", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Dattes", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Epinards", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Fenouil", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Figuies", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Fraises", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Framboises", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Fruits", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Gingembre", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Grapefruit", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Herbes", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Kaki", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Kiwi", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Legumes", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Lime", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Mais", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Mandarines", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Mangue", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Melon", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Menthe", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Mures", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Nectarine", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Oignons", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Olives", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Oranges", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Pasteque", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Peche", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Persil", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Petit pois", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Piment rouge", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Poireau", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Poires", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Poivron", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Pommes", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Pommes de terre", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Prunes", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Raisins", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Rhubarbe", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Rucola", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Salade", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Sauge", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Thym", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Tomates", 0, "desc1", false));
        childGroceryItemList.add(new Items("1", "Tomates Cerises", 0, "desc1", false));

        childGroceryItemList.add(new Items("1", "Stylo", 0, "None", false));
        childGroceryItemList.add(new Items("2", "Crayon", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Gomma per cancellare", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Taille-Crayon", 0, "None", false));
        childGroceryItemList.add(new Items("5", "Marqueur", 0, "None", false));
        childGroceryItemList.add(new Items("6", "Bloc-notes", 0, "None", false));
        childGroceryItemList.add(new Items("7", "Post-it", 0, "None", false));
        childGroceryItemList.add(new Items("8", "Trombones", 0, "None", false));


        childGroceryItemList.add(new Items("1", "Baguette", 0, "None", false));
        childGroceryItemList.add(new Items("2", "Croissants", 0, "None", false));
        childGroceryItemList.add(new Items("2", "Pain", 0, "None", false));
        childGroceryItemList.add(new Items("2", "Pain croustillant", 0, "None", false));
        childGroceryItemList.add(new Items("2", "Pate a pizza", 0, "None", false));
        childGroceryItemList.add(new Items("2", "Pate feuilletee", 0, "None", false));
        childGroceryItemList.add(new Items("2", "Petits pains", 0, "None", false));
        childGroceryItemList.add(new Items("2", "Toast", 0, "None", false));

        childGroceryItemList.add(new Items("3", "Beurre", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Beuree aux herbes", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Blue", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Cottage Cheese", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Creme", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Creme acidulee", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Creme fraiche", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Feta", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Fromage", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Fromage frais", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Fromage rape", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Gorgonzola", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Lait", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Lait de soja", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Margarine", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Mascarpone", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Mozzarella", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Oeufs", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Parmesan", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Ricotta", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Sere", 0, "None", false));
        childGroceryItemList.add(new Items("3", "Yogourt", 0, "None", false));


        childGroceryItemList.add(new Items("4", "Agneau", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Anchois", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Boeuf", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Charcuterie", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Emince de boeuf", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Escalope", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Escalopes de poulet", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Homard", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Jambon", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Jambon cru", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Lard", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Moules", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Poisson", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Poisson thon", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Poulet", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Salami", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Saucisse", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Saucisse a rotir", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Saucisson", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Saumon", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Thon", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Viande", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Viande de porc", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Viande de veau", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Viande hachee", 0, "None", false));

        childGroceryItemList.add(new Items("4", "Amandes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Bouillon", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cannelle", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Compote de pommes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cornichon", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Haricots", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Huile", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Huile d olive", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Ketchup", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Lait de coco", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Lentilles", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Levure", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Mayonnaise", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Moutrade", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Noisettes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Noix", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Origan", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Panure", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Paprika", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Pelati", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Poivre", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Poudrealever", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Puree de tomates", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Romarin", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sauce a salade", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sauce de roti", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sauce soja", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sauce tomate", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sel", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sucre", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sucre glace", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sucre vanille", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Vinaigre", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Vinaigre balsamique", 0, "None", false));

        childGroceryItemList.add(new Items("4", "Batonnets de poisson", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Croquettes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Glaces", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Lasagnes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Pizza", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Pommes frites", 0, "None", false));

        childGroceryItemList.add(new Items("4", "Avoine", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Corn Flakes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Couscous", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Farine", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Gnocchi", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Muesli", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Nouilles", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Pasta", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Penne", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Pois chiches", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Riz", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Riz Basmati", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Riz Risotto", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Semoule", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Spaghetti", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Tofu", 0, "None", false));


        childGroceryItemList.add(new Items("4", "Barre de cereales", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Biscuits", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Biscuits de Noel", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cacahuetes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Chewing Gum", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Chocolat", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Confiture", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Creme de nougat", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Gateau", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Grissini", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Miel", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Pain d'epice", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Pommes chips", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Pop Corn", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Snacks", 0, "None", false));

        childGroceryItemList.add(new Items("4", "Biere", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Boissons", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cacao", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cafe", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Capsules de cafe", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cidre", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cognac", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cola", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cola Light", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Dosettes de cafe", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Eau", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Eau minerale", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Eau tonique", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Energy Drink", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Gin", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Grains de cafe", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Jus d'orange", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Jus de fruits", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Jus de pomme", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Limonade", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Prosecco", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sirop", 0, "None", false));
        childGroceryItemList.add(new Items("4", "The", 0, "None", false));
        childGroceryItemList.add(new Items("4", "The froid", 0, "None", false));


        childGroceryItemList.add(new Items("4", "Adoucissant", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Alimentation bebe", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Ampoule", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Antidouleur", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Apres-Shampooing", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Baume a levres", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Bloc-notes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Body Lotion", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Bougies", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Brosse a dents", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cadeau", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Cotons-tiges", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Couches", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Crayon", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Creme mains", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Creme solaire", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Creme visage", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Demaquillant", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Dentifrice", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Deo", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Detergent", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Detergent salle de bain", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Dissolvant a ongles", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Douche Get", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Eau dentaire", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Eponge", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Essuie-tout", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Feuille d'aluminium", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Fil dentaire", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Film alimentaire", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Fleurs", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Gel cheveux", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Gomma per cancellare", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Hairspray", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Insectifuge", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Lames rasoir", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Lessive", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Lingettes humides", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Liquide vaisselle", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Marqueur", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Mouchoirs", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Mousse a raser", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Nettoyant", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Nettoyant vitres", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Nettoyant WC", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Papier sulfurise", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Papier toilette", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Papier-cadeau", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Piles", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Post-it", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Preservatifs", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Rasoir", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Rince-eclat", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Rondelles de ouate", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sacs a ordures", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Savon", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sel regenerant", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Serviettes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Serviettes demaquillantes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Shampooing", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Solution Lentiles", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Sparadraps", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Stylo", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Tablettes lave-vaisselle", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Taille-crayon", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Tampons", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Trombones", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Vernis a ongles", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Vitamine", 0, "None", false));

        childGroceryItemList.add(new Items("4", "Litiere pour chat", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Nourriture chats", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Nourriture chiens", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Nourriture olseaux", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Nourriture poissons", 0, "None", false));

        childGroceryItemList.add(new Items("4", "Arrosoir", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Charbon de bois", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Clou", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Engrais", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Gaz", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Grill", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Outils", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Parasol", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Pinceau", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Plantes", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Pot", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Semences", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Terreau", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Tondeuse", 0, "None", false));
        childGroceryItemList.add(new Items("4", "Vis", 0, "None", false));

       // searchedItemsList = childGroceryItemList;
      //  childListAdapter.notifyDataSetChanged();

        initRecyclerView();


    }
}
