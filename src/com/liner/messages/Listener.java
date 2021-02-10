package com.liner.messages;

import com.liner.models.User;
import com.liner.triggers.TriggerEntity;
import com.sun.istack.internal.Nullable;

public interface Listener<T extends TriggerEntity> {
    T getTrigger();
    void onTriggered(User sender, @Nullable User target, @Nullable String[] arguments);
}
