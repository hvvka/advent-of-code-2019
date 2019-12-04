def has_doubles(n):
    return len(set(str(n))) < len(str(n))


def are_digits_increasing(n):
    array = [int(d) for d in str(n)]
    return all(array[i] <= array[i + 1] for i in range(len(array) - 1))


def calculate_passwords(input_from, input_to):
    combinations = [i for i in range(input_from, input_to)]
    solutions = []
    for c in combinations:
        if has_doubles(c) and are_digits_increasing(c):
            solutions.append(c)
    return solutions


if __name__ == '__main__':
    solutions = calculate_passwords(356261, 846303)
    print(len(solutions))  # 544
