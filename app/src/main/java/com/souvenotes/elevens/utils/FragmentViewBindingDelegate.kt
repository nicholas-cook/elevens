package com.souvenotes.elevens.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty

/**
The official Android docs (https://developer.android.com/topic/libraries/view-binding) on ViewBinding
use the double bangs (!!) to dangerously assert non-null on the binding.
It's also pretty important that we null out all references to the views in
{@link androidx.fragment.app.Fragment#onDestroyView() } because a Fragment may outlive its view.

This delegate will let us get a non-null reference to our ViewBinding. We leverage the Fragment's
view and tag.  The trick here is that the View will be destroyed in onDestroyView, and so will its
tag, nulling out both the View and its tag (the binding).

Idea stolen from this Reddit post:
https://www.reddit.com/r/androiddev/comments/eo8rou/view_binding_docs_updated_with_info_on_fragment/fea42o9/
 */
inline fun <reified T : ViewBinding> Fragment.fragmentViewBinding(crossinline bind: (View) -> T): ReadOnlyProperty<Fragment, T> =
    ReadOnlyProperty<Fragment, T> { _, property ->
        val binding = view?.getTag(property.name.hashCode()) as? T
        binding ?: bind(requireView()).also {
            requireView().setTag(property.name.hashCode(), it)
        }
    }
