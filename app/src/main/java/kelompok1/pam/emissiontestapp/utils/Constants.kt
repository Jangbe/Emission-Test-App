package kelompok1.pam.emissiontestapp.utils

import kelompok1.pam.emissiontestapp.data.model.BottomNavItem
import kelompok1.pam.emissiontestapp.R

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            route = "home",
            activeIcon = R.drawable.ic_home_linear,
            inactiveIcon = R.drawable.ic_home_gray
        ),
        BottomNavItem(
            label = "Form",
            route = "form",
            activeIcon = R.drawable.ic_activity_linear,
            inactiveIcon = R.drawable.ic_activity_gray
        ),
        BottomNavItem(
            label = "Logo",
            route = "logo",
            activeIcon = R.drawable.car,
            inactiveIcon = R.drawable.car
        ),
        BottomNavItem(
            label = "Camera",
            route = "camera",
            activeIcon = R.drawable.ic_camera_linear,
            inactiveIcon = R.drawable.ic_camera_gray
        ),
        BottomNavItem(
            label = "Profile",
            route = "profile",
            activeIcon = R.drawable.ic_profile_linear,
            inactiveIcon = R.drawable.ic_profile_gray
        )
    )
}