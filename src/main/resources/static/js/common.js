function addInputField(divId, inputName) {
    // from text input
    const container = document.getElementById(divId);
    const newField = document.createElement("div");
    newField.className = "flex items-center space-x-2 mt-2";
    newField.innerHTML = `
                <input type="text" name="${inputName}" value=""
                       class="w-full p-2 rounded border border-teal-700 bg-teal-800 text-teal-200">
                <button type="button" onclick="addInputField('${divId}', '${inputName}')"
                        class="p-2 bg-teal-600 text-white rounded hover:bg-teal-700">+</button>
            `;
    container.appendChild(newField);
}

function addReadOnlyInput(selectId, containerId, inputName, rawSelectMap) {
    // from select box
    const selectBox = document.getElementById(selectId);
    const selectedValue = selectBox.value;
    const container = document.getElementById(containerId);
    rawSelectMap = rawSelectMap
        .replace('}]', '"}').slice(1).replace('name=', '"')
        .replaceAll('}, {name=', '", "').replaceAll(', value=', '": "');
    const selectMap = JSON.parse(rawSelectMap);

    if (selectedValue) {
        // 해당 값을 가진 옵션 이름을 확인
        const selectedName = selectMap[selectedValue];

        if (!selectedName) {
            return;
        }

        // 이미 존재하는지 확인
        const existingInputs = container.querySelectorAll(`input[name='${inputName}']`);
        for (const input of existingInputs) {
            if (input.value === selectedValue) {
                return; // 중복 추가 방지
            }
        }

        // 새로운 readonly input 추가 (이름용)
        const newNameInput = document.createElement("input");
        newNameInput.type = "text";
        newNameInput.name = inputName + "-visible";
        newNameInput.value = selectedName;
        newNameInput.readOnly = true;
        newNameInput.className = 'w-full p-2 rounded border border-teal-700 bg-teal-800 text-teal-200 mb-2';
        container.appendChild(newNameInput);

        // 새로운 readonly input 추가 (실제 값)
        const newValueInput = document.createElement("input");
        newValueInput.type = "hidden";
        newValueInput.name = inputName;
        newValueInput.value = selectedValue;
        newValueInput.readOnly = true;
        container.appendChild(newValueInput);
    }
}

function prepareFormSubmission(inputNames) {
    // on submit
    const container = document.getElementById('submit-form');

    for (const key_name in inputNames) {
        // 모든 hidden input 값을 리스트로 수집
        const selectedValues = Array.from(container.querySelectorAll(`input[name='${key_name}']`))
            .map(input => {
                console.log(input);
                return input.value;
            });

        // 기존 hidden input을 제거하고 하나의 리스트 값으로 추가
        const hiddenInput = document.createElement('input');
        hiddenInput.type = 'hidden';
        hiddenInput.name = inputNames[key_name];
        hiddenInput.value = JSON.stringify(selectedValues).slice(1, -1);
        container.appendChild(hiddenInput);
    }
    // 폼 제출
    return true;
}