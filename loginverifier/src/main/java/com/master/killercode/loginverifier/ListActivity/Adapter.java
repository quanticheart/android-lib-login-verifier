package com.master.killercode.loginverifier.ListActivity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.master.killercode.loginverifier.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //First
    private Activity activity;
    private int layout;
    private List<UserModel> dataBase;
    private UserModel user = new UserModel();
    private LayoutInflater layoutInflater;
    private int viewPosition;
    private Class nextIntent;

    //Second - View's Types
    private int NORMAL_TYPE = 0;
    private int INITLIST = 1;

    /**
     * init Constructor with HMAux's List
     *
     * @param activity   activity pai
     * @param dataBase   HMAux's list
     * @param nextIntent
     */
    public Adapter(Activity activity, List<UserModel> dataBase, Class nextIntent) {
        this.activity = activity;
        this.dataBase = dataBase;
        this.nextIntent = nextIntent;
        this.layoutInflater = LayoutInflater.from(activity);
    }


    /**
     * @param hmAux add data raw hmaux
     */
    public void addItem(UserModel hmAux) {
        dataBase.add(hmAux);
        notifyItemInserted(dataBase.size() - 1);
    }


    /**
     * remove at first position
     */
    public void removeFirstItem() {
        dataBase.remove(0);
        notifyItemRemoved(0);
    }

    /**
     * remove at last position
     */
    public void removeLastItem() {
        dataBase.remove(dataBase.size() - 1);
        notifyItemRemoved(dataBase.size());
    }

    /**
     * remove item at positon with parameter
     *
     * @param position
     */
    public void removeItemAtPosition(int position) {
        dataBase.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * remove all items at RecyclerView
     */
    public void clear() {
        final int size = dataBase.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                dataBase.remove(0);
            }
            notifyItemRemoved(0);
        }
    }

    /**
     * add FooterView at RecyclerView
     */
    public void addInitView() {
        user.setTypeIntent(String.valueOf(INITLIST));
        dataBase.add(user);
        notifyItemInserted(0);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ObjectAnimator animatorUP = ObjectAnimator.ofFloat(view, "translationZ", 20);
                    animatorUP.setDuration(200);
                    animatorUP.setInterpolator(new DecelerateInterpolator());
                    animatorUP.start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    ObjectAnimator animatorDOWN = ObjectAnimator.ofFloat(view, "translationZ", 0);
                    animatorDOWN.setDuration(200);
                    animatorDOWN.setInterpolator(new AccelerateInterpolator());
                    animatorDOWN.start();
                    break;
            }
            return false;
        }
    };

    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * in class implements onMoveAndSwipedListener to use methods at down
     */

//    @Override
//    public boolean onItemMove(int fromPosition, int toPosition) {
//        Collections.swap(mItems, fromPosition, toPosition);
//        notifyItemMoved(fromPosition, toPosition);
//        return true;
//    }
//
//    @Override
//    public void onItemDismiss(final int position) {
//        mItems.remove(position);
//        notifyItemRemoved(position);
//
//        Snackbar.make(parentView, "Item Deleted", Snackbar.LENGTH_SHORT)
//                .setAction("Undo", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        addItem(position, mItems.get(position));
//                    }
//                }).show();
//    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == INITLIST) {
            View view = layoutInflater.inflate(R.layout.cell_init, parent, false);
            return new initViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.cell_user, parent, false);
            return new RecyclerViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        user = dataBase.get(position);
        if (holder instanceof initViewHolder) {
            initViewHolder viewHolder = (initViewHolder) holder;
            viewHolder.view.setOnTouchListener(touchListener);
            viewHolder.view.setOnClickListener(Init_ViewClick);
            viewHolder.view.setTag(position);
            initActions_initView(viewHolder, user);

        } else {
            RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;
            viewHolder.view.setOnTouchListener(touchListener);
            viewHolder.view.setTag(position);
            initActions_NormalView(viewHolder, user, position);
        }
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param position
     * @return ViewType for load Resorce
     */
    @Override
    public int getItemViewType(int position) {
        UserModel s = dataBase.get(position);
        String typeView = s.getTypeIntent();

        if (typeView.length() > 0) {

            if (typeView.equals(String.valueOf(INITLIST))) {
                return INITLIST;
            } else {
                return NORMAL_TYPE;
            }

        } else {
            return NORMAL_TYPE;
        }
    }

    /**
     * @return HMAux's List size
     */
    @Override
    public int getItemCount() {
        return dataBase.size();
    }

    /**
     * ViewHolder INITVIEW
     */
    private class initViewHolder extends RecyclerView.ViewHolder {
        private View view;

        private ImageView popMenu;

        public initViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            popMenu = itemView.findViewById(R.id.popmenu);
        }
    }

    /**
     * @param viewHolder
     * @param userData
     */
    private void initActions_initView(final initViewHolder viewHolder, UserModel userData) {

        viewHolder.popMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(activity, viewHolder.popMenu);
                menu.getMenuInflater().inflate(R.menu.initmenu, menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        if (id == R.id.clearlist) {
//                            Toast.makeText(activity, "Funfo", Toast.LENGTH_SHORT).show();
                            GetDataBase get = new GetDataBase(activity);
                            get.deleteDataBaseUser();
                            goNextActivity(nextIntent);
                        }

                        return true;
                    }
                });

                menu.show();
            }
        });

    }

    private View.OnClickListener Init_ViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goNextActivity(nextIntent);
        }
    };

    private void goNextActivity(Class nextIntent) {
        Intent intent = new Intent(activity, nextIntent);
        activity.startActivity(intent);
        activity.finish();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * ViewHolder NORMAL_TYPE
     */
    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private View view;
        // view
        private TextView nome;
        private TextView datalog;
        private TextView datalogout;
        private LinearLayout lllog;
        private LinearLayout lllogout;
        private ImageView popMenu;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            //findViewById's

            nome = itemView.findViewById(R.id.nome);
            datalog = itemView.findViewById(R.id.datalog);
            datalogout = itemView.findViewById(R.id.datalogout);
            lllog = itemView.findViewById(R.id.lllog);
            lllogout = itemView.findViewById(R.id.lllogout);
            popMenu = itemView.findViewById(R.id.popmenuuser);

            /**
             * Events at View
             */
            view.setOnClickListener(NORMAL_TYPE_ViewClick);
            view.setOnLongClickListener(NORMAL_TYPE_ViewLongClick);
        }
    }

    private void initActions_NormalView(final RecyclerViewHolder viewHolder, final UserModel user, final int position) {

        String userName = user.getUserName();
        String login = user.getUser();
        String dateLog = user.getDateLog();
        String dateLogout = user.getDateLogout();

        if (verifieString(userName)) {
            viewHolder.nome.setText(login);
        } else {
            viewHolder.nome.setText(userName);
        }

        if (verifieString(dateLog)) {
            viewHolder.lllog.setVisibility(View.INVISIBLE);
            viewHolder.datalog.setText(dateLog);
        } else {
            viewHolder.lllog.setVisibility(View.VISIBLE);
            viewHolder.datalog.setText(dateLog);
        }

        if (verifieString(dateLogout)) {
            viewHolder.lllogout.setVisibility(View.INVISIBLE);
            viewHolder.datalogout.setText(dateLogout);
        } else {
            viewHolder.lllogout.setVisibility(View.VISIBLE);
            viewHolder.datalogout.setText(dateLogout);
        }

        viewHolder.popMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final PopupMenu menu = new PopupMenu(activity, viewHolder.popMenu);
                menu.getMenuInflater().inflate(R.menu.usermenu, menu.getMenu());

                final MenuItem passMenu = menu.getMenu().findItem(R.id.passremove);
                final GetDataBase data = new GetDataBase(activity);

                String pass = user.getPass();
                Log.w("Pass", pass);
                if (pass.equals("")) {
                    passMenu.setVisible(false);
                } else {
                    passMenu.setVisible(true);
                }


                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        if (id == R.id.userremove) {
                            Toast.makeText(activity, "Removendo usuario", Toast.LENGTH_SHORT).show();
                            data.removeUser(user.getId());
                            removeItemAtPosition(position);
                        } else if (id == R.id.passremove) {
                            Toast.makeText(activity, "Removendo senha", Toast.LENGTH_SHORT).show();
                            data.removePass(user.getId());
                            user.setPass("");
                            passMenu.setVisible(false);
                        }

                        return true;
                    }
                });

                menu.show();
            }
        });

    }

    private Boolean verifieString(String s) {
        return s.equals("") || s.equals("null");
    }

    private View.OnClickListener NORMAL_TYPE_ViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            viewPosition = (int) view.getTag();
            UserModel userModel = dataBase.get(viewPosition);

            Intent intent = new Intent(activity, nextIntent);
            intent.putExtra(UserModel.getDataUser, userModel);
            activity.startActivity(intent);
            activity.finish();
        }
    };

    private View.OnLongClickListener NORMAL_TYPE_ViewLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {

            viewPosition = (int) v.getTag();

            Toast.makeText(activity, "longClick", Toast.LENGTH_SHORT).show();

            return true;
        }
    };


}
