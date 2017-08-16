Type=Class
Version=6.8
ModulesStructureVersion=1
B4A=true
@EndOfDesignText@
Sub Class_Globals
	Private nativeMe As JavaObject
	Dim setanimat As String
End Sub

'Initializes the object. You can add parameters to this method if needed.
Public Sub Initialize
	nativeMe=Me
End Sub


#if java 

public class SlideAnimationUtil {

    /**
     * Animates a view so that it slides in from the left of it's container.
     *
     * @param context
     * @param view
     */
    public static void slideInFromLeft(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_left);
    }

    /**
     * Animates a view so that it slides from its current position, out of view to the left.
     *
     * @param context
     * @param view
     */
    public static void slideOutToLeft(Context context, View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_left);
    }

    /**
     * Runs a simple animation on a View with no extra parameters.
     *
     * @param context
     * @param view
     * @param animationId
     */
    private static void runSimpleAnimation(Context context, View view, int animationId) {
        view.startAnimation(AnimationUtils.loadAnimation(
                context, animationId
        ));
    }

}



#End If