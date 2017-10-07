package com.example.akif.devinfo.utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.akif.devinfo.R;

import java.util.List;

public class FragmentUtil {

    AppCompatActivity mActivity;

    public FragmentUtil(AppCompatActivity mActivity) {
        this.mActivity = mActivity;
    }

    public void addFragment(final Fragment fragment, final boolean isAddToBackStack, final boolean shouldAnimate)
    {
        pushFragment(fragment,null, R.id.fragment_container, isAddToBackStack, true, shouldAnimate, false);
    }

    public void replaceFragment(final Fragment fragment, final boolean isAddToBackStack, final boolean shouldAnimate)
    {
        pushFragment(fragment,null, R.id.fragment_container, isAddToBackStack, false, shouldAnimate, false);
    }

    public void addFragmentIgnorIfCurrent(final Fragment fragment, final boolean isAddToBackStack, final boolean shouldAnimate)
    {
        pushFragment(fragment,null,R.id.fragment_container, isAddToBackStack, true, shouldAnimate, true);
    }
    public void replaceFragmentIgnorIfCurrent(final Fragment fragment, final boolean isAddToBackStack, final boolean shouldAnimate)
    {
        pushFragment(fragment,null,R.id.fragment_container, isAddToBackStack, false, shouldAnimate, true);
    }

    //

    public void addChildFragment(final Fragment fragment, final Fragment parentFragment, final int containerId, final boolean isAddToBackStack, final boolean shouldAnimate)
    {
        pushFragment(fragment,parentFragment,containerId, isAddToBackStack, true, shouldAnimate, false);
    }
    public void replaceChildFragment(final Fragment fragment, final Fragment parentFragment, final int containerId, final boolean isAddToBackStack, final boolean shouldAnimate)
    {
        pushFragment(fragment,parentFragment,containerId, isAddToBackStack, false, shouldAnimate, false);
    }

    public void addChildFragmentIgnorIfCurrent(final Fragment fragment, final Fragment parentFragment, final int containerId, final boolean isAddToBackStack, final boolean shouldAnimate)
    {
        pushFragment(fragment,parentFragment,containerId, isAddToBackStack, true, shouldAnimate, true);
    }
    public void replaceChildFragmentIgnorIfCurrent(final Fragment fragment, final Fragment parentFragment, final int containerId, final boolean isAddToBackStack, final boolean shouldAnimate)
    {
        pushFragment(fragment,parentFragment,containerId, isAddToBackStack, false, shouldAnimate, true);
    }


    private void pushFragment(final Fragment fragment, final Fragment parentFragment, final int containerId, boolean isAddToBackStack, boolean isJustAdd, final boolean shouldAnimate, final boolean ignorIfCurrent)
    {
        if (fragment == null)
            return;
        // Add the fragment to the 'fragment_container' FrameLayout
        final FragmentManager fragmentManager;// = getSupportFragmentManager();

        if (parentFragment != null)
        {
            fragmentManager = parentFragment.getChildFragmentManager();
        }
        else {
            fragmentManager = mActivity.getSupportFragmentManager();
        }


        // Find current visible fragment
        Fragment fragmentCurrent = fragmentManager.findFragmentById(R.id.fragment_container);

        if (ignorIfCurrent && fragmentCurrent!=null) {
            if (fragment.getClass().getCanonicalName().equalsIgnoreCase(fragmentCurrent.getTag())) {
                return;
            }
        }


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (shouldAnimate) {
//            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        }

        if (fragmentCurrent != null)
        {
            fragmentTransaction.hide(fragmentCurrent);
        }

        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getCanonicalName());
        }
        else {

           /* List<Fragment> fragmentList = fragmentManager.getFragments();
            if (fragmentList != null && !fragmentList.isEmpty())
            {
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for (Fragment fragmentM : fragmentList)
                {
                    if (fragmentM != null)
                    {
                        fragmentTransaction.remove(fragmentM);
                    }
                }
//                fragmentTransaction.commit();
            }*/
        }

        if (isJustAdd) {
            fragmentTransaction.add(containerId, fragment, fragment.getClass().getCanonicalName());
        }
        else {
            fragmentTransaction.replace(containerId, fragment, fragment.getClass().getCanonicalName());
        }


        try {
//            fragmentTransaction.commit();
            fragmentTransaction.commitAllowingStateLoss();

            if (!isAddToBackStack) {
//                fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            }

            Methods.hideKeyboard(mActivity);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public Fragment getCurrentFragment()
    {
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        // Find current visible fragment
        return fragmentManager.findFragmentById(R.id.fragment_container);
    }

        public void clearBackStackFragmets()
    {

       /* if (1==1)
            return;*/
        try {
            // in my case I get the support fragment manager, it should work with the native one too
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            // this will clear the back stack and displays no animation on the screen
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            // fragmentManager.popBackStackImmediate(SplashFragment.class.getCanonicalName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);
            List<Fragment> fragmentList = fragmentManager.getFragments();
            if (fragmentList != null && !fragmentList.isEmpty())
            {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                for (Fragment fragment : fragmentList)
                {
                    if (fragment != null)
                    {
                        fragmentTransaction.remove(fragment);
                    }
                }
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    public void clearBackStackFragmets(FragmentManager fragmentManager)
    {
        // in my case I get the support fragment manager, it should work with the native one too
//        FragmentManager fragmentManager = getSupportFragmentManager();
        // this will clear the back stack and displays no animation on the screen
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        // fragmentManager.popBackStackImmediate(SplashFragment.class.getCanonicalName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);

        List<Fragment> fragmentList = fragmentManager.getFragments();
        if (fragmentList != null && !fragmentList.isEmpty())
        {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (Fragment fragment : fragmentList)
            {
                if (fragment != null)
                {
                    fragmentTransaction.remove(fragment);
                }
            }
            fragmentTransaction.commit();
        }

//        Methods.hideKeyboard();


    }

    public void addTabClildFragment(Fragment fragmentParent, Fragment fragmentChild)
    {
        if (fragmentParent != null && fragmentChild != null)
        {
            FragmentManager fragmentManager = fragmentParent.getChildFragmentManager();
            clearBackStackFragmets(fragmentManager);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, fragmentChild, fragmentChild.getClass().getCanonicalName());
            fragmentTransaction.commit();
        }
    }

    public void clearBackStackFragmets(final String tag)
    {
        // in my case I get the support fragment manager, it should work with the native one too
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        // this will clear the back stack and displays no animation on the screen
        fragmentManager.popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        // fragmentManager.popBackStackImmediate(SplashFragment.class.getCanonicalName(),FragmentManager.POP_BACK_STACK_INCLUSIVE);

//        Methods.hideKeyboard();
    }

}
